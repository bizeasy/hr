import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { HrTestModule } from '../../../test.module';
import { WorkEffortInventoryAssignComponent } from 'app/entities/work-effort-inventory-assign/work-effort-inventory-assign.component';
import { WorkEffortInventoryAssignService } from 'app/entities/work-effort-inventory-assign/work-effort-inventory-assign.service';
import { WorkEffortInventoryAssign } from 'app/shared/model/work-effort-inventory-assign.model';

describe('Component Tests', () => {
  describe('WorkEffortInventoryAssign Management Component', () => {
    let comp: WorkEffortInventoryAssignComponent;
    let fixture: ComponentFixture<WorkEffortInventoryAssignComponent>;
    let service: WorkEffortInventoryAssignService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortInventoryAssignComponent],
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
        .overrideTemplate(WorkEffortInventoryAssignComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortInventoryAssignComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortInventoryAssignService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkEffortInventoryAssign(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workEffortInventoryAssigns && comp.workEffortInventoryAssigns[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkEffortInventoryAssign(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workEffortInventoryAssigns && comp.workEffortInventoryAssigns[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
