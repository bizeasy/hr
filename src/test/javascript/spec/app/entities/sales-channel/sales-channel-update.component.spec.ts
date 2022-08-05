import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { SalesChannelUpdateComponent } from 'app/entities/sales-channel/sales-channel-update.component';
import { SalesChannelService } from 'app/entities/sales-channel/sales-channel.service';
import { SalesChannel } from 'app/shared/model/sales-channel.model';

describe('Component Tests', () => {
  describe('SalesChannel Management Update Component', () => {
    let comp: SalesChannelUpdateComponent;
    let fixture: ComponentFixture<SalesChannelUpdateComponent>;
    let service: SalesChannelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [SalesChannelUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SalesChannelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SalesChannelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SalesChannelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SalesChannel(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SalesChannel();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
