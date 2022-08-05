import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { HrTestModule } from '../../../test.module';
import { WorkEffortAssocComponent } from 'app/entities/work-effort-assoc/work-effort-assoc.component';
import { WorkEffortAssocService } from 'app/entities/work-effort-assoc/work-effort-assoc.service';
import { WorkEffortAssoc } from 'app/shared/model/work-effort-assoc.model';

describe('Component Tests', () => {
  describe('WorkEffortAssoc Management Component', () => {
    let comp: WorkEffortAssocComponent;
    let fixture: ComponentFixture<WorkEffortAssocComponent>;
    let service: WorkEffortAssocService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortAssocComponent],
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
        .overrideTemplate(WorkEffortAssocComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortAssocComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortAssocService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkEffortAssoc(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workEffortAssocs && comp.workEffortAssocs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkEffortAssoc(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workEffortAssocs && comp.workEffortAssocs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
