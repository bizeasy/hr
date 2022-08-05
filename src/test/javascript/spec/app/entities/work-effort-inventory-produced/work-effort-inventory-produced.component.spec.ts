import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { HrTestModule } from '../../../test.module';
import { WorkEffortInventoryProducedComponent } from 'app/entities/work-effort-inventory-produced/work-effort-inventory-produced.component';
import { WorkEffortInventoryProducedService } from 'app/entities/work-effort-inventory-produced/work-effort-inventory-produced.service';
import { WorkEffortInventoryProduced } from 'app/shared/model/work-effort-inventory-produced.model';

describe('Component Tests', () => {
  describe('WorkEffortInventoryProduced Management Component', () => {
    let comp: WorkEffortInventoryProducedComponent;
    let fixture: ComponentFixture<WorkEffortInventoryProducedComponent>;
    let service: WorkEffortInventoryProducedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortInventoryProducedComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(WorkEffortInventoryProducedComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortInventoryProducedComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortInventoryProducedService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkEffortInventoryProduced(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workEffortInventoryProduceds && comp.workEffortInventoryProduceds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkEffortInventoryProduced(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workEffortInventoryProduceds && comp.workEffortInventoryProduceds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
